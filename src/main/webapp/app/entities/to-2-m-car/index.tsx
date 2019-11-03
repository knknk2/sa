import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import To2mCar from './to-2-m-car';
import To2mCarDetail from './to-2-m-car-detail';
import To2mCarUpdate from './to-2-m-car-update';
import To2mCarDeleteDialog from './to-2-m-car-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={To2mCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={To2mCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={To2mCarDetail} />
      <ErrorBoundaryRoute path={match.url} component={To2mCar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={To2mCarDeleteDialog} />
  </>
);

export default Routes;
