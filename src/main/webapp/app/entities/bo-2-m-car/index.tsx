import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bo2mCar from './bo-2-m-car';
import Bo2mCarDetail from './bo-2-m-car-detail';
import Bo2mCarUpdate from './bo-2-m-car-update';
import Bo2mCarDeleteDialog from './bo-2-m-car-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Bo2mCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Bo2mCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Bo2mCarDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bo2mCar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Bo2mCarDeleteDialog} />
  </>
);

export default Routes;
