import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import To2mCarInf from './to-2-m-car-inf';
import To2mCarInfDetail from './to-2-m-car-inf-detail';
import To2mCarInfUpdate from './to-2-m-car-inf-update';
import To2mCarInfDeleteDialog from './to-2-m-car-inf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={To2mCarInfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={To2mCarInfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={To2mCarInfDetail} />
      <ErrorBoundaryRoute path={match.url} component={To2mCarInf} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={To2mCarInfDeleteDialog} />
  </>
);

export default Routes;
