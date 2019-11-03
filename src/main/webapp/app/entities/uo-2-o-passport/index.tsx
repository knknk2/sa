import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Uo2oPassport from './uo-2-o-passport';
import Uo2oPassportDetail from './uo-2-o-passport-detail';
import Uo2oPassportUpdate from './uo-2-o-passport-update';
import Uo2oPassportDeleteDialog from './uo-2-o-passport-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Uo2oPassportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Uo2oPassportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Uo2oPassportDetail} />
      <ErrorBoundaryRoute path={match.url} component={Uo2oPassport} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Uo2oPassportDeleteDialog} />
  </>
);

export default Routes;
