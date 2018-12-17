import React from 'react';
import {Labels} from '../../constants';
import {Image} from 'react-bootstrap/lib';

class AboutPage extends React.Component {
  render() {
    return (
      <div className="container">
        <div className="row mb-1">
          <div className="col-12">
            <h1>{Labels.about.title}</h1>
          </div>
        </div>
        <div className="row mb-4">
          <div className="col-7">
            <Image src={require('../../images/young-doctor-consulting.jpg')} style={{width: "auto", height: "15em"}} alt={Labels.about.image_description} />
          </div>
          <div className="col-5">
            {Labels.about.description}
          </div>
        </div>
      </div>
    );
  }
}

export default AboutPage;
