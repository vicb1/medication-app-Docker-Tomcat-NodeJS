// This component handles the App template used on every page.
import React from 'react';
import PropTypes from 'prop-types';
import Header from './common/Header';
import { Route, Switch, withRouter } from "react-router-dom";
import HomePage from './home/HomePage';
import DashboardPage from './dashboard/DashboardPage';
import AboutPage from './about/AboutPage';
import NotFoundPage from "./NotFoundPage";
import PrivateRoute from './PrivateRoute';
import LoginPage from './login/LoginPage';
import { hot } from 'react-hot-loader';
import {connect} from 'react-redux';
import {Labels} from '../constants';
import {Image} from 'react-bootstrap/lib';

// This is a class-based component because the current
// version of hot reloading won't hot reload a stateless
// component at the top-level.

class App extends React.Component {
  render() {
    return (
      <div className="container-fluid">
        <Header
          loading={this.props.loading}
          loginStatus={this.props.loginStatus}
        />
        <div className="jumbotron">
          <Switch>
              <PrivateRoute exact path="/" component={HomePage} />
              <PrivateRoute path="/about" component={AboutPage} />
              <Route path="/login" component={LoginPage} />
              <PrivateRoute path="/dashboard" component={DashboardPage} />
              <PrivateRoute component={NotFoundPage} />
          </Switch>
        </div>
        <div className="row justify-content-center mb-5">
          {Labels.footer.text}<Image src={require('../images/yellow_jacket_logo.jpg')} style={{width: "auto", height: "2.5em", marginLeft: ".3em", marginTop: "-1em"}} alt={Labels.footer.image_description} />
        </div>
      </div>
    );
  }
}

App.propTypes = {
  loading: PropTypes.bool.isRequired,
  loginStatus: PropTypes.string.isRequired
};

function mapStateToProps(state/*, ownProps*/) {
  return {
    loading: 0 < state.ajaxCallsInProgress,
    loginStatus: state.loginStatus
  };
}

export default withRouter(connect(mapStateToProps)(hot(module)(App)));