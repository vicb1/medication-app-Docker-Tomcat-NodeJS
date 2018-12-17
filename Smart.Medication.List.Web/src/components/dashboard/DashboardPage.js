import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as patientActions from '../../actions/patientActions';
import {Labels} from '../../constants';
import {Button} from 'react-bootstrap/lib';
import FieldGroup from '../common/FieldGroup';
import PatientInfoWithUmsListModel from '../../models/PatientInfoWithUmsList';
import BootstrapTable from 'react-bootstrap-table-next';
import toastr from 'toastr';

function medicationFormatter(cell, row, rowIndex) { // eslint-disable-line
  return (
  <div className="container">
    <div className="row">
      {row.drugName}
    </div>
    <div className="row mt-4">
      {row.drugDescription}
    </div>
    <div className="row mt-2">
      <Button bsStyle="outline-secondary" onClick={onDeleteMedication.bind(this,rowIndex)}><i className="fa fa-trash fa-fw" aria-hidden="true" />&nbsp;{Labels.dashboard.delete_medication_button_label}</Button>
    </div>
  </div>);
}

function onDeleteMedication(rowIndex) { // eslint-disable-line
  toastr.error(Labels.not_implemented.title);
}

function routeFormatter(cell, row, rowIndex) { // eslint-disable-line
  return cell === "Oral" ? Labels.dosing_schedule.routes.oral_label : cell;
}

function drugDoseFormatter(cell, row, rowIndex) { // eslint-disable-line
  return cell ? row.drugDose : "";
}

const columns = [{
  dataField: 'id',
  text: '',
  hidden: true
}, {
  dataField: 'text',
  text: Labels.dosing_schedule.medication_header,
  formatter: medicationFormatter
}, {
  dataField: 'drugRoute',
  text: Labels.dosing_schedule.route_header,
  formatter: routeFormatter
}, {
  dataField: 'breakfast',
  text: Labels.dosing_schedule.breakfast_header,
  formatter: drugDoseFormatter,
  headerStyle: { width: '6em' }
}, {
  dataField: 'lunch',
  text: Labels.dosing_schedule.lunch_header,
  formatter: drugDoseFormatter,
  headerStyle: { width: '6em' }
}, {
  dataField: 'dinner',
  text: Labels.dosing_schedule.dinner_header,
  formatter: drugDoseFormatter,
  headerStyle: { width: '6em' }
}, {
  dataField: 'bedtime',
  text: Labels.dosing_schedule.bedtime_header,
  formatter: drugDoseFormatter,
  headerStyle: { width: '6em' }
}];

class DashboardPage extends React.Component {
  constructor(props, context) {
    super(props, context);

    this.onGetUmsDataForPatient = this.onGetUmsDataForPatient.bind(this);
  }

  onGetUmsDataForPatient(){
    const {actions} = this.props;

    if (!this.patientId.value) {
      toastr.error(`${Labels.dashboard.patient_id_label} required.`);
      return;
    }
    actions.loadPatientInfoWithUmsList(this.patientId.value)
    .catch(error => {
      if (error.response && error.response.status === 404) {
        toastr.error(Labels.dashboard.patient_not_found_message);
      } else {
        toastr.error(Labels.common.unknown_error_message);
      }
    });
  }

  render() {
    const {patientInfoWithUmsList} = this.props;
    return (
      <div>
        <h1>{Labels.dashboard.title}</h1>
        <div className="row mt-3">
          <div className="col-3">
            <FieldGroup
              id="patientId"
              type="text"
              label={Labels.dashboard.patient_id_label}
              maxLength={10}
              style={{width: "10em"}}
              inputRef={ref => { this.patientId = ref; }}
            />
          </div>
          <div className="col-9 mt-auto" style={{marginBottom: "1rem"}}>
            <Button bsStyle="primary" onClick={this.onGetUmsDataForPatient}>{Labels.dashboard.submit_button_label}</Button>
          </div>
        </div>
        {patientInfoWithUmsList.patientFullName &&
        <React.Fragment>
          <div className="row">
            <div className="card">
              <div className="card-body">
                <b>{Labels.common.patient_name}</b>: { patientInfoWithUmsList.patientFullName }
              </div>
            </div>
          </div>
          <div className="row">
            <div className="card">
              <div className="card-body">
                <BootstrapTable keyField='id' data={ patientInfoWithUmsList.umsList } columns={ columns } />
              </div>
            </div>
          </div>
        </React.Fragment>
        }
      </div>
    );
  }
}

DashboardPage.propTypes = {
  patientInfoWithUmsList: PropTypes.instanceOf(PatientInfoWithUmsListModel).isRequired,
  actions: PropTypes.object.isRequired
};

function mapStateToProps(state/*, ownProps*/) {
  return {
    patientInfoWithUmsList: state.patientInfoWithUmsList
  };
}

function mapDispatchToProps(dispatch) {
  return {
    actions: bindActionCreators(patientActions, dispatch)
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(DashboardPage);
