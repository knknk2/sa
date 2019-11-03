import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import To2mPersonInf from './to-2-m-person-inf';
import To2mPersonInfDetail from './to-2-m-person-inf-detail';
import To2mPersonInfUpdate from './to-2-m-person-inf-update';
import To2mPersonInfDeleteDialog from './to-2-m-person-inf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={To2mPersonInfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={To2mPersonInfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={To2mPersonInfDetail} />
      <ErrorBoundaryRoute path={match.url} component={To2mPersonInf} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={To2mPersonInfDeleteDialog} />
  </>
);

export default Routes;
