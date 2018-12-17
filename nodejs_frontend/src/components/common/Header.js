import React from 'react';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';
import LoadingDots from './LoadingDots';
import {Labels, LoginTypeEnum} from '../../constants';
import LoginModel from '../../models/Login';
import {Image} from 'react-bootstrap/lib';
import {connect} from 'react-redux';

const Header = ({loading, loginStatus, loggedInUser}) => { // eslint-disable-line
  return (
    <div>
      <header className="py-3">
        <div className="row flex-nowrap justify-content-between align-items-center">
          <div className="col-2 pt-1"/>
          <div className="col-7" style={{marginBottom: "-1em"}}>
            <div className="container">
              <div className="row justify-content-center">
                <Image src={require('../../images/smart.png')} alt={Labels.app_title} style={{width: "auto", height: "3.5em", marginTop: "-.25em"}} />
                <h4 style={{marginTop: ".5em"}}>{Labels.abbreviated_app_title}</h4>
              </div>
            </div>
            {loading && <LoadingDots interval={100} dots={20}/>}
          </div>
          <div className="col-2 d-flex justify-content-end align-items-center">
            <NavLink onClick={() => { if (loginStatus){ location.reload(true); } }} to="/login" className="btn btn-sm btn-outline-secondary"><i className={`fas ${loginStatus ? "fa-sign-out-alt" : "fa-sign-in-alt"} fa-fw`} aria-hidden="true" />&nbsp; {loginStatus ? Labels.logout.title : Labels.login.title}</NavLink>
          </div>
        </div>
      </header>
      <nav className="navbar navbar-expand-md navbar-dark bg-dark">
        <div className="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
          {loginStatus &&
          <ul className="navbar-nav mr-auto">
            {loginStatus !== LoginTypeEnum.logged_out &&
            <li className="nav-item">
              <NavLink exact to="/" className="nav-link" activeClassName="active"><i className="fa fa-home fa-fw" aria-hidden="true" />&nbsp; {Labels.home.title}</NavLink>
            </li>
            }
            {loginStatus === LoginTypeEnum.patient &&
            <li className="nav-item">
              <NavLink to="/dashboard" className="nav-link" activeClassName="active"><i className="fas fa-file-prescription fa-fw" aria-hidden="true" />&nbsp; {Labels.dashboard.title}</NavLink>
            </li>
            }
          </ul>
          }
        </div>
        <div className="mx-auto order-0">
          {loginStatus &&
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
            <span className="navbar-toggler-icon"></span>
          </button>
          }
        </div>
        <div className="navbar-collapse collapse w-100 order-3 dual-collapse2">
          {loginStatus &&
          <ul className="navbar-nav ml-auto">
            {loginStatus !== LoginTypeEnum.logged_out &&
            <li className="nav-item">
              <NavLink to="/about" className="nav-link" activeClassName="active"><i className="fa fa-info-circle fa-fw" aria-hidden="true" />&nbsp; {Labels.about.title}</NavLink>
            </li>
            }
          </ul>
          }
        </div>
      </nav>
    </div>
  );
};

Header.propTypes = {
  loading: PropTypes.bool.isRequired,
  loginStatus: PropTypes.string.isRequired,
  loggedInUser: PropTypes.instanceOf(LoginModel).isRequired
};

function mapStateToProps(state/*, ownProps*/) {
  return {
      loading: state.ajaxCallsInProgress > 0,
      loginStatus: state.loginStatus,
      loggedInUser: state.loggedInUser
  };
}

export default connect(mapStateToProps)(Header);