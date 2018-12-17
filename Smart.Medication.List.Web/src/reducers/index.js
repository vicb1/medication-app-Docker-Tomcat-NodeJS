import { combineReducers } from 'redux';
import loginStatus from './loginStatusReducer';
import loggedInUser from './loggedInUserReducer';
import patientInfoWithUmsList from './patientInfoWithUmsListReducer'
import ajaxCallsInProgress from './ajaxStatusReducer';
import { routerReducer } from 'react-router-redux';

const rootReducer = combineReducers({
    loginStatus,
    loggedInUser,
    patientInfoWithUmsList,
    ajaxCallsInProgress,
    routing: routerReducer
});

export default rootReducer;