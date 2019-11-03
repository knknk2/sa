import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import To2mPerson from './to-2-m-person';
import To2mPersonDetail from './to-2-m-person-detail';
import To2mPersonUpdate from './to-2-m-person-update';
import To2mPersonDeleteDialog from './to-2-m-person-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={To2mPersonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={To2mPersonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={To2mPersonDetail} />
      <ErrorBoundaryRoute path={match.url} component={To2mPerson} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={To2mPersonDeleteDialog} />
  </>
);

export default Routes;
