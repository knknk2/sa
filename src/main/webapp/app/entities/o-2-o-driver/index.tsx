import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import O2oDriver from './o-2-o-driver';
import O2oDriverDetail from './o-2-o-driver-detail';
import O2oDriverUpdate from './o-2-o-driver-update';
import O2oDriverDeleteDialog from './o-2-o-driver-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={O2oDriverUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={O2oDriverUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={O2oDriverDetail} />
      <ErrorBoundaryRoute path={match.url} component={O2oDriver} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={O2oDriverDeleteDialog} />
  </>
);

export default Routes;
