import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import O2oCar from './o-2-o-car';
import O2oCarDetail from './o-2-o-car-detail';
import O2oCarUpdate from './o-2-o-car-update';
import O2oCarDeleteDialog from './o-2-o-car-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={O2oCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={O2oCarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={O2oCarDetail} />
      <ErrorBoundaryRoute path={match.url} component={O2oCar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={O2oCarDeleteDialog} />
  </>
);

export default Routes;
