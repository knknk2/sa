import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Um2oOwner from './um-2-o-owner';
import Um2oOwnerDetail from './um-2-o-owner-detail';
import Um2oOwnerUpdate from './um-2-o-owner-update';
import Um2oOwnerDeleteDialog from './um-2-o-owner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Um2oOwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Um2oOwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Um2oOwnerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Um2oOwner} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Um2oOwnerDeleteDialog} />
  </>
);

export default Routes;
