import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Fields from './fields';
import Bo2mOwnerDTO from './bo-2-m-owner-dto';
import Bo2mOwner from './bo-2-m-owner';
import Bo2mCar from './bo-2-m-car';
import Bo2mCarDTO from './bo-2-m-car-dto';
import Um2oOwner from './um-2-o-owner';
import Um2oCar from './um-2-o-car';
import To2mPerson from './to-2-m-person';
import To2mPersonInf from './to-2-m-person-inf';
import To2mCar from './to-2-m-car';
import To2mCarInf from './to-2-m-car-inf';
import M2mDriver from './m-2-m-driver';
import M2mDriverDTOMF from './m-2-m-driver-dtomf';
import M2mCarDTOMF from './m-2-m-car-dtomf';
import M2mCar from './m-2-m-car';
import O2oDriver from './o-2-o-driver';
import O2oCar from './o-2-o-car';
import Uo2oPassport from './uo-2-o-passport';
import Uo2oPassportDTOMF from './uo-2-o-passport-dtomf';
import Uo2oCitizen from './uo-2-o-citizen';
import Uo2oCitizenDTOMF from './uo-2-o-citizen-dtomf';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/fields`} component={Fields} />
      <ErrorBoundaryRoute path={`${match.url}/bo-2-m-owner-dto`} component={Bo2mOwnerDTO} />
      <ErrorBoundaryRoute path={`${match.url}/bo-2-m-owner`} component={Bo2mOwner} />
      <ErrorBoundaryRoute path={`${match.url}/bo-2-m-car`} component={Bo2mCar} />
      <ErrorBoundaryRoute path={`${match.url}/bo-2-m-car-dto`} component={Bo2mCarDTO} />
      <ErrorBoundaryRoute path={`${match.url}/um-2-o-owner`} component={Um2oOwner} />
      <ErrorBoundaryRoute path={`${match.url}/um-2-o-car`} component={Um2oCar} />
      <ErrorBoundaryRoute path={`${match.url}/to-2-m-person`} component={To2mPerson} />
      <ErrorBoundaryRoute path={`${match.url}/to-2-m-person-inf`} component={To2mPersonInf} />
      <ErrorBoundaryRoute path={`${match.url}/to-2-m-car`} component={To2mCar} />
      <ErrorBoundaryRoute path={`${match.url}/to-2-m-car-inf`} component={To2mCarInf} />
      <ErrorBoundaryRoute path={`${match.url}/m-2-m-driver`} component={M2mDriver} />
      <ErrorBoundaryRoute path={`${match.url}/m-2-m-driver-dtomf`} component={M2mDriverDTOMF} />
      <ErrorBoundaryRoute path={`${match.url}/m-2-m-car-dtomf`} component={M2mCarDTOMF} />
      <ErrorBoundaryRoute path={`${match.url}/m-2-m-car`} component={M2mCar} />
      <ErrorBoundaryRoute path={`${match.url}/o-2-o-driver`} component={O2oDriver} />
      <ErrorBoundaryRoute path={`${match.url}/o-2-o-car`} component={O2oCar} />
      <ErrorBoundaryRoute path={`${match.url}/uo-2-o-passport`} component={Uo2oPassport} />
      <ErrorBoundaryRoute path={`${match.url}/uo-2-o-passport-dtomf`} component={Uo2oPassportDTOMF} />
      <ErrorBoundaryRoute path={`${match.url}/uo-2-o-citizen`} component={Uo2oCitizen} />
      <ErrorBoundaryRoute path={`${match.url}/uo-2-o-citizen-dtomf`} component={Uo2oCitizenDTOMF} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
