import {get} from '../apiWrapper';

class PatientApi {
  static loadPatientInfoWithUmsList(patientId) {
    return get(`sml2/node?patientId=${patientId}`);
  }
}

export default PatientApi;