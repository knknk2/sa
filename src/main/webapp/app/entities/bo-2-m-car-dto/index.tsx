import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bo2mCarDTO from './bo-2-m-car-dto';
import Bo2mCarDTODetail from './bo-2-m-car-dto-detail';
import Bo2mCarDTOUpdate from './bo-2-m-car-dto-update';
import Bo2mCarDTODeleteDialog from './bo-2-m-car-dto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Bo2mCarDTOUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Bo2mCarDTOUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Bo2mCarDTODetail} />
      <ErrorBoundaryRoute path={match.url} component={Bo2mCarDTO} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Bo2mCarDTODeleteDialog} />
  </>
);

export default Routes;
