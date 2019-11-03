import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import M2mCar from './m-2-m-car';
import M2mCarDetail from './m-2-m-car-detail';
import M2mCarUpdate from './m-2-m-car-update';
import M2mCarDeleteDialog from './m-2-m-car-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={M2mCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={M2mCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={M2mCarDetail} />
      <ErrorBoundaryRoute path={match.url} component={M2mCar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={M2mCarDeleteDialog} />
  </>
);

export default Routes;
