import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Uo2oPassportDTOMF from './uo-2-o-passport-dtomf';
import Uo2oPassportDTOMFDetail from './uo-2-o-passport-dtomf-detail';
import Uo2oPassportDTOMFUpdate from './uo-2-o-passport-dtomf-update';
import Uo2oPassportDTOMFDeleteDialog from './uo-2-o-passport-dtomf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Uo2oPassportDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Uo2oPassportDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Uo2oPassportDTOMFDetail} />
      <ErrorBoundaryRoute path={match.url} component={Uo2oPassportDTOMF} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Uo2oPassportDTOMFDeleteDialog} />
  </>
);

export default Routes;
