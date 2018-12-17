import * as types from '../actions/actionTypes';
import initialState from './initialState';

export default function lessonReducer(state = initialState.patientInfoWithUmsList, action) {
  switch (action.type) {
    case types.LOAD_PATIENT_INFO_WITH_UMS_LIST_SUCCESS:
    case types.LOAD_PATIENT_INFO_WITH_UMS_LIST_NOT_FOUND:
      return action.patientInfoWithUmsList;
    default:
      return state;
  }
}