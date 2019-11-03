import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IM2mCar } from 'app/shared/model/m-2-m-car.model';
import { getEntities as getM2MCars } from 'app/entities/m-2-m-car/m-2-m-car.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-2-m-driver.reducer';
import { IM2mDriver } from 'app/shared/model/m-2-m-driver.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IM2mDriverUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IM2mDriverUpdateState {
  isNew: boolean;
  m2mCarId: string;
}

export class M2mDriverUpdate extends React.Component<IM2mDriverUpdateProps, IM2mDriverUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      m2mCarId: '0',
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

    this.props.getM2MCars();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { m2mDriverEntity } = this.props;
      const entity = {
        ...m2mDriverEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/m-2-m-driver');
  };

  render() {
    const { m2mDriverEntity, m2mCars, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.m2mDriver.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.m2mDriver.home.createOrEditLabel">Create or edit a M2mDriver</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : m2mDriverEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="m-2-m-driver-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="m-2-m-driver-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="m-2-m-driver-name">
                    <Translate contentKey="sampleappApp.m2mDriver.name">Name</Translate>
                  </Label>
                  <AvField id="m-2-m-driver-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-2-m-driver" replace color="info">
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
  m2mCars: storeState.m2mCar.entities,
  m2mDriverEntity: storeState.m2mDriver.entity,
  loading: storeState.m2mDriver.loading,
  updating: storeState.m2mDriver.updating,
  updateSuccess: storeState.m2mDriver.updateSuccess
});

const mapDispatchToProps = {
  getM2MCars,
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
)(M2mDriverUpdate);
