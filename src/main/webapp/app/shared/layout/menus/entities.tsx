import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/fields">
      <Translate contentKey="global.menu.entities.fields" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/bo-2-m-owner-dto">
      <Translate contentKey="global.menu.entities.bo2MOwnerDto" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/bo-2-m-owner">
      <Translate contentKey="global.menu.entities.bo2MOwner" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/bo-2-m-car">
      <Translate contentKey="global.menu.entities.bo2MCar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/bo-2-m-car-dto">
      <Translate contentKey="global.menu.entities.bo2MCarDto" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/um-2-o-owner">
      <Translate contentKey="global.menu.entities.um2OOwner" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/um-2-o-car">
      <Translate contentKey="global.menu.entities.um2OCar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/to-2-m-person">
      <Translate contentKey="global.menu.entities.to2MPerson" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/to-2-m-person-inf">
      <Translate contentKey="global.menu.entities.to2MPersonInf" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/to-2-m-car">
      <Translate contentKey="global.menu.entities.to2MCar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/to-2-m-car-inf">
      <Translate contentKey="global.menu.entities.to2MCarInf" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/m-2-m-driver">
      <Translate contentKey="global.menu.entities.m2MDriver" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/m-2-m-driver-dtomf">
      <Translate contentKey="global.menu.entities.m2MDriverDtomf" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/m-2-m-car-dtomf">
      <Translate contentKey="global.menu.entities.m2MCarDtomf" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/m-2-m-car">
      <Translate contentKey="global.menu.entities.m2MCar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/o-2-o-driver">
      <Translate contentKey="global.menu.entities.o2ODriver" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/o-2-o-car">
      <Translate contentKey="global.menu.entities.o2OCar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/uo-2-o-passport">
      <Translate contentKey="global.menu.entities.uo2OPassport" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/uo-2-o-passport-dtomf">
      <Translate contentKey="global.menu.entities.uo2OPassportDtomf" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/uo-2-o-citizen">
      <Translate contentKey="global.menu.entities.uo2OCitizen" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/uo-2-o-citizen-dtomf">
      <Translate contentKey="global.menu.entities.uo2OCitizenDtomf" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
