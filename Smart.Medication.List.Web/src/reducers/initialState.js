import {LoginTypeEnum} from '../constants';
import LoginModel from '../models/Login';
import PatientInfoWithUmsListModel from '../models/PatientInfoWithUmsList';

export default {
    loginStatus: LoginTypeEnum.logged_out,
    loggedInUser: new LoginModel(),
    patientInfoWithUmsList: new PatientInfoWithUmsListModel(),
    ajaxCallsInProgress: 0
}