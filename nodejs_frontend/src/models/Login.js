export default class Login {
  constructor(params) {
      this.name = params ? params.name : null;
      this.email = params ? params.email : null;
      this.isPatient = params ? params.isPatient : null;
  }
}