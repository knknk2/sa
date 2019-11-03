import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import fields, {
  FieldsState
} from 'app/entities/fields/fields.reducer';
// prettier-ignore
import bo2mOwnerDTO, {
  Bo2mOwnerDTOState
} from 'app/entities/bo-2-m-owner-dto/bo-2-m-owner-dto.reducer';
// prettier-ignore
import bo2mOwner, {
  Bo2mOwnerState
} from 'app/entities/bo-2-m-owner/bo-2-m-owner.reducer';
// prettier-ignore
import bo2mCar, {
  Bo2mCarState
} from 'app/entities/bo-2-m-car/bo-2-m-car.reducer';
// prettier-ignore
import bo2mCarDTO, {
  Bo2mCarDTOState
} from 'app/entities/bo-2-m-car-dto/bo-2-m-car-dto.reducer';
// prettier-ignore
import um2oOwner, {
  Um2oOwnerState
} from 'app/entities/um-2-o-owner/um-2-o-owner.reducer';
// prettier-ignore
import um2oCar, {
  Um2oCarState
} from 'app/entities/um-2-o-car/um-2-o-car.reducer';
// prettier-ignore
import to2mPerson, {
  To2mPersonState
} from 'app/entities/to-2-m-person/to-2-m-person.reducer';
// prettier-ignore
import to2mPersonInf, {
  To2mPersonInfState
} from 'app/entities/to-2-m-person-inf/to-2-m-person-inf.reducer';
// prettier-ignore
import to2mCar, {
  To2mCarState
} from 'app/entities/to-2-m-car/to-2-m-car.reducer';
// prettier-ignore
import to2mCarInf, {
  To2mCarInfState
} from 'app/entities/to-2-m-car-inf/to-2-m-car-inf.reducer';
// prettier-ignore
import m2mDriver, {
  M2mDriverState
} from 'app/entities/m-2-m-driver/m-2-m-driver.reducer';
// prettier-ignore
import m2mDriverDTOMF, {
  M2mDriverDTOMFState
} from 'app/entities/m-2-m-driver-dtomf/m-2-m-driver-dtomf.reducer';
// prettier-ignore
import m2mCarDTOMF, {
  M2mCarDTOMFState
} from 'app/entities/m-2-m-car-dtomf/m-2-m-car-dtomf.reducer';
// prettier-ignore
import m2mCar, {
  M2mCarState
} from 'app/entities/m-2-m-car/m-2-m-car.reducer';
// prettier-ignore
import o2oDriver, {
  O2oDriverState
} from 'app/entities/o-2-o-driver/o-2-o-driver.reducer';
// prettier-ignore
import o2oCar, {
  O2oCarState
} from 'app/entities/o-2-o-car/o-2-o-car.reducer';
// prettier-ignore
import uo2oPassport, {
  Uo2oPassportState
} from 'app/entities/uo-2-o-passport/uo-2-o-passport.reducer';
// prettier-ignore
import uo2oPassportDTOMF, {
  Uo2oPassportDTOMFState
} from 'app/entities/uo-2-o-passport-dtomf/uo-2-o-passport-dtomf.reducer';
// prettier-ignore
import uo2oCitizen, {
  Uo2oCitizenState
} from 'app/entities/uo-2-o-citizen/uo-2-o-citizen.reducer';
// prettier-ignore
import uo2oCitizenDTOMF, {
  Uo2oCitizenDTOMFState
} from 'app/entities/uo-2-o-citizen-dtomf/uo-2-o-citizen-dtomf.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly fields: FieldsState;
  readonly bo2mOwnerDTO: Bo2mOwnerDTOState;
  readonly bo2mOwner: Bo2mOwnerState;
  readonly bo2mCar: Bo2mCarState;
  readonly bo2mCarDTO: Bo2mCarDTOState;
  readonly um2oOwner: Um2oOwnerState;
  readonly um2oCar: Um2oCarState;
  readonly to2mPerson: To2mPersonState;
  readonly to2mPersonInf: To2mPersonInfState;
  readonly to2mCar: To2mCarState;
  readonly to2mCarInf: To2mCarInfState;
  readonly m2mDriver: M2mDriverState;
  readonly m2mDriverDTOMF: M2mDriverDTOMFState;
  readonly m2mCarDTOMF: M2mCarDTOMFState;
  readonly m2mCar: M2mCarState;
  readonly o2oDriver: O2oDriverState;
  readonly o2oCar: O2oCarState;
  readonly uo2oPassport: Uo2oPassportState;
  readonly uo2oPassportDTOMF: Uo2oPassportDTOMFState;
  readonly uo2oCitizen: Uo2oCitizenState;
  readonly uo2oCitizenDTOMF: Uo2oCitizenDTOMFState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  fields,
  bo2mOwnerDTO,
  bo2mOwner,
  bo2mCar,
  bo2mCarDTO,
  um2oOwner,
  um2oCar,
  to2mPerson,
  to2mPersonInf,
  to2mCar,
  to2mCarInf,
  m2mDriver,
  m2mDriverDTOMF,
  m2mCarDTOMF,
  m2mCar,
  o2oDriver,
  o2oCar,
  uo2oPassport,
  uo2oPassportDTOMF,
  uo2oCitizen,
  uo2oCitizenDTOMF,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
