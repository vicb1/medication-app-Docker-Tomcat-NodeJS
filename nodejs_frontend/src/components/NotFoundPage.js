import React from 'react';
import { Link } from 'react-router-dom';
import {Labels} from '../constants';

const NotFoundPage = () => {
    return (
        <div>
            <h4>
                {Labels.not_found.title}
            </h4>
            <Link to="/"> {Labels.not_found.message} </Link>
        </div>
    );
};

export default NotFoundPage;