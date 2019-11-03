import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Um2oCar from './um-2-o-car';
import Um2oCarDetail from './um-2-o-car-detail';
import Um2oCarUpdate from './um-2-o-car-update';
import Um2oCarDeleteDialog from './um-2-o-car-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Um2oCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Um2oCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Um2oCarDetail} />
      <ErrorBoundaryRoute path={match.url} component={Um2oCar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Um2oCarDeleteDialog} />
  </>
);

export default Routes;
