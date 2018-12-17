import * as types from '../actions/actionTypes';
import initialState from './initialState';
import {LoginTypeEnum} from '../constants';

export default function loginReducer(state = initialState.loginStatus, action) {
  if (action.type === types.LOGIN_PATIENT_SUCCESS) {
    return LoginTypeEnum.patient;
  } else if (action.type === types.LOGOUT_SUCCESS) {
    return LoginTypeEnum.logged_out;
  }

  return state;
}