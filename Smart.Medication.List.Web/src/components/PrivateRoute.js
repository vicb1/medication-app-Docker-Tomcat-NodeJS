import React from 'react'
import {
  Route,
  Redirect,
  withRouter
} from 'react-router-dom'
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

const PrivateRoute = ({ component: Component, ...rest }) => {
    return <Route {...rest} render={(props, loginStatus = rest.loginStatus) => {
        return loginStatus
          ? <Component {...props} />
          : <Redirect to={{pathname: '/login', state: { from: props.location }}} />
      }} />
};

PrivateRoute.propTypes = {
    component: PropTypes.func.isRequired,
    location: PropTypes.object.isRequired,
    loginStatus: PropTypes.string.isRequired
};

function mapStateToProps(state/*, ownProps*/) {
    return {
        loginStatus: state.loginStatus
    };
}

export default withRouter(connect(mapStateToProps)(PrivateRoute));