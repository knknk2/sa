import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import M2mCarDTOMF from './m-2-m-car-dtomf';
import M2mCarDTOMFDetail from './m-2-m-car-dtomf-detail';
import M2mCarDTOMFUpdate from './m-2-m-car-dtomf-update';
import M2mCarDTOMFDeleteDialog from './m-2-m-car-dtomf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={M2mCarDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={M2mCarDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={M2mCarDTOMFDetail} />
      <ErrorBoundaryRoute path={match.url} component={M2mCarDTOMF} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={M2mCarDTOMFDeleteDialog} />
  </>
);

export default Routes;
