import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Uo2oCitizenDTOMF from './uo-2-o-citizen-dtomf';
import Uo2oCitizenDTOMFDetail from './uo-2-o-citizen-dtomf-detail';
import Uo2oCitizenDTOMFUpdate from './uo-2-o-citizen-dtomf-update';
import Uo2oCitizenDTOMFDeleteDialog from './uo-2-o-citizen-dtomf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Uo2oCitizenDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Uo2oCitizenDTOMFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Uo2oCitizenDTOMFDetail} />
      <ErrorBoundaryRoute path={match.url} component={Uo2oCitizenDTOMF} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Uo2oCitizenDTOMFDeleteDialog} />
  </>
);

export default Routes;
