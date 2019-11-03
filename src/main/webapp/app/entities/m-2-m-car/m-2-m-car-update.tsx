import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IM2mDriver } from 'app/shared/model/m-2-m-driver.model';
import { getEntities as getM2MDrivers } from 'app/entities/m-2-m-driver/m-2-m-driver.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-2-m-car.reducer';
import { IM2mCar } from 'app/shared/model/m-2-m-car.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IM2mCarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IM2mCarUpdateState {
  isNew: boolean;
  idsm2mDriver: any[];
}

export class M2mCarUpdate extends React.Component<IM2mCarUpdateProps, IM2mCarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsm2mDriver: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getM2MDrivers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { m2mCarEntity } = this.props;
      const entity = {
        ...m2mCarEntity,
        ...values,
        m2mDrivers: mapIdList(values.m2mDrivers)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/m-2-m-car');
  };

  render() {
    const { m2mCarEntity, m2mDrivers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.m2mCar.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.m2mCar.home.createOrEditLabel">Create or edit a M2mCar</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : m2mCarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="m-2-m-car-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="m-2-m-car-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="m-2-m-car-name">
                    <Translate contentKey="sampleappApp.m2mCar.name">Name</Translate>
                  </Label>
                  <AvField id="m-2-m-car-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="m-2-m-car-m2mDriver">
                    <Translate contentKey="sampleappApp.m2mCar.m2mDriver">M 2 M Driver</Translate>
                  </Label>
                  <AvInput
                    id="m-2-m-car-m2mDriver"
                    type="select"
                    multiple
                    className="form-control"
                    name="m2mDrivers"
                    value={m2mCarEntity.m2mDrivers && m2mCarEntity.m2mDrivers.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {m2mDrivers
                      ? m2mDrivers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-2-m-car" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  m2mDrivers: storeState.m2mDriver.entities,
  m2mCarEntity: storeState.m2mCar.entity,
  loading: storeState.m2mCar.loading,
  updating: storeState.m2mCar.updating,
  updateSuccess: storeState.m2mCar.updateSuccess
});

const mapDispatchToProps = {
  getM2MDrivers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(M2mCarUpdate);
