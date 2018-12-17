export default class UmsItem {
  constructor(params) {
    this.id = params ? params.id : 0;
    this.drugName = params ? params.drugName : '';
    this.drugDescription = params ? params.drugDescription : '';
    this.drugRoute = params ? params.drugRoute : '';
    this.drugDose = params ? params.drugDose : '';
    this.breakfast = params ? params.breakfast : false;
    this.lunch = params ? params.lunch : false;
    this.dinner = params ? params.dinner : false;
    this.bedtime = params ? params.bedtime : false;
    this.text = params ? params.text : '';
  }
}