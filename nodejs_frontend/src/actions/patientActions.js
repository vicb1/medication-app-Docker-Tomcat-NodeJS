import * as types from './actionTypes';
import patientApi from '../api/patientApi';
import {beginAjaxCall} from './ajaxStatusActions';
import PatientInfoWithUmsListModel from '../models/PatientInfoWithUmsList';
import UmsItemModel from '../models/UmsItem';

export function loadPatientInfoWithUmsListSuccess(patientInfoWithUmsList) {
  return { type: types.LOAD_PATIENT_INFO_WITH_UMS_LIST_SUCCESS, patientInfoWithUmsList};
}

export function loadPatientInfoWithUmsListNotFound() {
  return { type: types.LOAD_PATIENT_INFO_WITH_UMS_LIST_NOT_FOUND, patientInfoWithUmsList: new PatientInfoWithUmsListModel()};
}

function jsonParseUmsItem(item) {
  const umsItem = Object.assign({}, item);
  return new UmsItemModel(umsItem);
}

function jsonParsePatientInfoWithUmsList(data) {
  const patientInfoWithUmsList = Object.assign({}, data);
  patientInfoWithUmsList.umsList = patientInfoWithUmsList.umsList ? patientInfoWithUmsList.umsList.map(i => jsonParseUmsItem(i)) : [];
  return new PatientInfoWithUmsListModel(patientInfoWithUmsList);
}

export function loadPatientInfoWithUmsList(patientId) {
  return function(dispatch) {
    dispatch(beginAjaxCall());
    return patientApi.loadPatientInfoWithUmsList(patientId)
    .then(response => dispatch(loadPatientInfoWithUmsListSuccess(jsonParsePatientInfoWithUmsList(response.data))))
    .catch(error => {
      dispatch(loadPatientInfoWithUmsListNotFound());
      throw(error);
    });
  };
}