import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IO2oCar } from 'app/shared/model/o-2-o-car.model';
import { getEntities as getO2OCars } from 'app/entities/o-2-o-car/o-2-o-car.reducer';
import { getEntity, updateEntity, createEntity, reset } from './o-2-o-driver.reducer';
import { IO2oDriver } from 'app/shared/model/o-2-o-driver.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IO2oDriverUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IO2oDriverUpdateState {
  isNew: boolean;
  o2oCarId: string;
}

export class O2oDriverUpdate extends React.Component<IO2oDriverUpdateProps, IO2oDriverUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      o2oCarId: '0',
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

    this.props.getO2OCars();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { o2oDriverEntity } = this.props;
      const entity = {
        ...o2oDriverEntity,
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
    this.props.history.push('/entity/o-2-o-driver');
  };

  render() {
    const { o2oDriverEntity, o2oCars, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.o2oDriver.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.o2oDriver.home.createOrEditLabel">Create or edit a O2oDriver</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : o2oDriverEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="o-2-o-driver-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="o-2-o-driver-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="o-2-o-driver-name">
                    <Translate contentKey="sampleappApp.o2oDriver.name">Name</Translate>
                  </Label>
                  <AvField id="o-2-o-driver-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/o-2-o-driver" replace color="info">
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
  o2oCars: storeState.o2oCar.entities,
  o2oDriverEntity: storeState.o2oDriver.entity,
  loading: storeState.o2oDriver.loading,
  updating: storeState.o2oDriver.updating,
  updateSuccess: storeState.o2oDriver.updateSuccess
});

const mapDispatchToProps = {
  getO2OCars,
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
)(O2oDriverUpdate);
