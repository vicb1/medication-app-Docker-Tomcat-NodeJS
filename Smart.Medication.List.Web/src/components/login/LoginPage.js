import React from 'react';
import PropTypes from 'prop-types';
import {Labels} from '../../constants';
import {Redirect, withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as loginActions from '../../actions/loginActions';
import ReactSignupLoginComponent from 'react-signup-login-component';
import {Modal, Button} from 'react-bootstrap/lib';
import LoginModel from '../../models/Login';

class LoginPage extends React.Component {
  constructor(props, context) {
    super(props, context);

    this.state = {
        redirectToReferrer: false,
        showModal: false,
        modalTitle: "",
        modalMessage: ""
    }

    this.signupWasClickedCallback = this.signupWasClickedCallback.bind(this);
    this.loginWasClickedCallback = this.loginWasClickedCallback.bind(this);
    this.recoverPasswordWasClickedCallback = this.recoverPasswordWasClickedCallback.bind(this);
    this.handleClose = this.handleClose.bind(this);
  }

  signupWasClickedCallback = () => {
    this.setState({ showModal: true, modalTitle: Labels.not_implemented.title, modalMessage: Labels.signup.not_implemented_message });
  }
  loginWasClickedCallback = (data) => {
    if (!data.username || !data.password) { // crude client-side validation
      this.setState({ showModal: true, modalTitle: Labels.login.error_title, modalMessage: Labels.login.error_message });
      return;
    }
    this.props.actions.loginPatient(new LoginModel({
      name: data.username,
      isPatient: true
    }))
    .then(() => {
        this.setState(() => ({redirectToReferrer: true}));
    });
  }
  recoverPasswordWasClickedCallback = () => {
    this.setState({ showModal: true, modalTitle: Labels.not_implemented.title, modalMessage: Labels.recover_password.not_implemented_message });
  }
  handleClose() {
    this.setState({ showModal: false });
  }

  render() {
    const { from } = this.props.location.state || { from: { pathname: '/' } }
    const { redirectToReferrer, showModal, modalTitle, modalMessage } = this.state

    if (redirectToReferrer === true) {
      return <Redirect to={from} />
    }

    return (
      <React.Fragment>
        <div className="container">
          <div className="row justify-content-center">
            <ReactSignupLoginComponent
              title={Labels.login.title}
              handleSignup={this.signupWasClickedCallback}
              handleLogin={this.loginWasClickedCallback}
              handleRecoverPassword={this.recoverPasswordWasClickedCallback}
              submitLoginCustomLabel={Labels.login.login_button_label}
            />
          </div>
        </div>
        <Modal show={showModal} animation={false} onHide={this.handleClose} dialogClassName="modal-lg">
          <Modal.Header>
            <Modal.Title>{modalTitle}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {modalMessage}
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={this.handleClose}>Close</Button>
          </Modal.Footer>
        </Modal>
      </React.Fragment>
    );
  }
}

LoginPage.propTypes = {
  location: PropTypes.object.isRequired,
  loginStatus: PropTypes.string.isRequired,
  actions: PropTypes.object.isRequired
};

function mapStateToProps(state/*, ownProps*/) {
  return {
      loginStatus: state.loginStatus
  };
}

function mapDispatchToProps(dispatch) {
  return {
    actions: bindActionCreators(loginActions, dispatch)
  };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(LoginPage));
