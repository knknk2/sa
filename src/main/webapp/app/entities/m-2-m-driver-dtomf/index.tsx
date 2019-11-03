import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import M2mDriverDTOMF from './m-2-m-driver-dtomf';
import M2mDriverDTOMFDetail from './m-2-m-driver-dtomf-detail';
import M2mDriverDTOMFUpdate from './m-2-m-driver-dtomf-update';
import M2mDriverDTOMFDeleteDialog from './m-2-m-driver-dtomf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={M2mDriverDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={M2mDriverDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={M2mDriverDTOMFDetail} />
      <ErrorBoundaryRoute path={match.url} component={M2mDriverDTOMF} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={M2mDriverDTOMFDeleteDialog} />
  </>
);

export default Routes;
