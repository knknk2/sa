import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Uo2oCitizen from './uo-2-o-citizen';
import Uo2oCitizenDetail from './uo-2-o-citizen-detail';
import Uo2oCitizenUpdate from './uo-2-o-citizen-update';
import Uo2oCitizenDeleteDialog from './uo-2-o-citizen-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={Uo2oCitizenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={Uo2oCitizenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={Uo2oCitizenDetail} />
      <ErrorBoundaryRoute path={match.url} component={Uo2oCitizen} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={Uo2oCitizenDeleteDialog} />
  </>
);

export default Routes;
