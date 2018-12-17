import * as types from './actionTypes';
import loginApi from '../api/loginApi';
import {beginAjaxCall, ajaxCallError} from './ajaxStatusActions';

export function loginPatientSuccess(patient){
    return {type: types.LOGIN_PATIENT_SUCCESS, patient};
}

export function logoutSuccess(){
    return {type: types.LOGOUT_SUCCESS};
}

export function loginPatient(loginModel) {
    return function (dispatch) {
      dispatch(beginAjaxCall());
      return loginApi.login(loginModel)
        .then(() => dispatch(loginPatientSuccess(loginModel)))
        .catch(error => {
            dispatch(ajaxCallError(error));
            throw(error);
        });
    };
  }

  export function logout() {
    return function (dispatch) {
      dispatch(beginAjaxCall());
      return loginApi.logout()
        .then(() => dispatch(logoutSuccess()))
        .catch(error => {
            dispatch(ajaxCallError(error));
            throw(error);
        });
    };
  }