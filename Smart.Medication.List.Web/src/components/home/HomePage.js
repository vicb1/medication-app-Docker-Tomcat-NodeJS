import React from 'react';
import {Link} from 'react-router-dom';
import {Labels} from '../../constants';
import {Image} from 'react-bootstrap/lib';

class HomePage extends React.Component {
  render() {
    return (
      <div className="container">
        <div className="row mb-1">
          <div className="col-12">
            <h1>{Labels.app_title}</h1>
          </div>
        </div>
        <div className="row mb-4">
          <div className="col-7">
            <Image src={require('../../images/woman-consulting-doctor.jpg')} style={{width: "auto", height: "20em"}} alt={Labels.home.image_description} />
          </div>
          <div className="col-5">
            {Labels.app_description}
          </div>
        </div>
        <div className="row">
          <div className="col-12">
            <Link to="about" className="btn btn-primary btn-lg">{Labels.home.learn_more}</Link>
          </div>
        </div>
      </div>
    );
  }
}

export default HomePage;