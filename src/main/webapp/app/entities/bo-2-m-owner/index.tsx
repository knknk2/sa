import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bo2mOwner from './bo-2-m-owner';
import Bo2mOwnerDetail from './bo-2-m-owner-detail';
import Bo2mOwnerUpdate from './bo-2-m-owner-update';
import Bo2mOwnerDeleteDialog from './bo-2-m-owner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Bo2mOwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Bo2mOwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Bo2mOwnerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bo2mOwner} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Bo2mOwnerDeleteDialog} />
  </>
);

export default Routes;
