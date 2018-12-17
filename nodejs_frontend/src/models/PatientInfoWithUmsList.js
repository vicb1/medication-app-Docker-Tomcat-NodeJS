export default class PatientInfoWithUmsList {
  constructor(params) {
    this.patientFullName = params ? params.patientFullName : '';
    this.umsList = params && params.umsList && params.umsList.constructor === Array ? params.umsList : []
  }
}