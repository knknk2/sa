import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Fields from './fields';
import FieldsDetail from './fields-detail';
import FieldsUpdate from './fields-update';
import FieldsDeleteDialog from './fields-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FieldsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FieldsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Fields} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FieldsDeleteDialog} />
  </>
);

export default Routes;
