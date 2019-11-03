import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import M2mDriver from './m-2-m-driver';
import M2mDriverDetail from './m-2-m-driver-detail';
import M2mDriverUpdate from './m-2-m-driver-update';
import M2mDriverDeleteDialog from './m-2-m-driver-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={M2mDriverUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={M2mDriverUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={M2mDriverDetail} />
      <ErrorBoundaryRoute path={match.url} component={M2mDriver} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={M2mDriverDeleteDialog} />
  </>
);

export default Routes;
