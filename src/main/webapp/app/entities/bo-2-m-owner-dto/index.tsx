import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bo2mOwnerDTO from './bo-2-m-owner-dto';
import Bo2mOwnerDTODetail from './bo-2-m-owner-dto-detail';
import Bo2mOwnerDTOUpdate from './bo-2-m-owner-dto-update';
import Bo2mOwnerDTODeleteDialog from './bo-2-m-owner-dto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Bo2mOwnerDTOUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Bo2mOwnerDTOUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Bo2mOwnerDTODetail} />
      <ErrorBoundaryRoute path={match.url} component={Bo2mOwnerDTO} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Bo2mOwnerDTODeleteDialog} />
  </>
);

export default Routes;
