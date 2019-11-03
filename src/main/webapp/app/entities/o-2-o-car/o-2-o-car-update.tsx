import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IO2oDriver } from 'app/shared/model/o-2-o-driver.model';
import { getEntities as getO2ODrivers } from 'app/entities/o-2-o-driver/o-2-o-driver.reducer';
import { getEntity, updateEntity, createEntity, reset } from './o-2-o-car.reducer';
import { IO2oCar } from 'app/shared/model/o-2-o-car.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IO2oCarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IO2oCarUpdateState {
  isNew: boolean;
  o2oDriverId: string;
}

export class O2oCarUpdate extends React.Component<IO2oCarUpdateProps, IO2oCarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      o2oDriverId: '0',
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

    this.props.getO2ODrivers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { o2oCarEntity } = this.props;
      const entity = {
        ...o2oCarEntity,
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
    this.props.history.push('/entity/o-2-o-car');
  };

  render() {
    const { o2oCarEntity, o2oDrivers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.o2oCar.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.o2oCar.home.createOrEditLabel">Create or edit a O2oCar</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : o2oCarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="o-2-o-car-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="o-2-o-car-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="o-2-o-car-name">
                    <Translate contentKey="sampleappApp.o2oCar.name">Name</Translate>
                  </Label>
                  <AvField
                    id="o-2-o-car-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="o-2-o-car-o2oDriver">
                    <Translate contentKey="sampleappApp.o2oCar.o2oDriver">O 2 O Driver</Translate>
                  </Label>
                  <AvInput id="o-2-o-car-o2oDriver" type="select" className="form-control" name="o2oDriver.id">
                    <option value="" key="0" />
                    {o2oDrivers
                      ? o2oDrivers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/o-2-o-car" replace color="info">
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
  o2oDrivers: storeState.o2oDriver.entities,
  o2oCarEntity: storeState.o2oCar.entity,
  loading: storeState.o2oCar.loading,
  updating: storeState.o2oCar.updating,
  updateSuccess: storeState.o2oCar.updateSuccess
});

const mapDispatchToProps = {
  getO2ODrivers,
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
)(O2oCarUpdate);
