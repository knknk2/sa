import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITo2mPerson } from 'app/shared/model/to-2-m-person.model';
import { getEntities as getTo2MPeople } from 'app/entities/to-2-m-person/to-2-m-person.reducer';
import { getEntity, updateEntity, createEntity, reset } from './to-2-m-car.reducer';
import { ITo2mCar } from 'app/shared/model/to-2-m-car.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITo2mCarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITo2mCarUpdateState {
  isNew: boolean;
  to2mOwnerId: string;
  to2mDriverId: string;
}

export class To2mCarUpdate extends React.Component<ITo2mCarUpdateProps, ITo2mCarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      to2mOwnerId: '0',
      to2mDriverId: '0',
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

    this.props.getTo2MPeople();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { to2mCarEntity } = this.props;
      const entity = {
        ...to2mCarEntity,
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
    this.props.history.push('/entity/to-2-m-car');
  };

  render() {
    const { to2mCarEntity, to2mPeople, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.to2mCar.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.to2mCar.home.createOrEditLabel">Create or edit a To2mCar</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : to2mCarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="to-2-m-car-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="to-2-m-car-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="to-2-m-car-name">
                    <Translate contentKey="sampleappApp.to2mCar.name">Name</Translate>
                  </Label>
                  <AvField id="to-2-m-car-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="to-2-m-car-to2mOwner">
                    <Translate contentKey="sampleappApp.to2mCar.to2mOwner">To 2 M Owner</Translate>
                  </Label>
                  <AvInput id="to-2-m-car-to2mOwner" type="select" className="form-control" name="to2mOwnerId">
                    <option value="" key="0" />
                    {to2mPeople
                      ? to2mPeople.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="to-2-m-car-to2mDriver">
                    <Translate contentKey="sampleappApp.to2mCar.to2mDriver">To 2 M Driver</Translate>
                  </Label>
                  <AvInput id="to-2-m-car-to2mDriver" type="select" className="form-control" name="to2mDriverId">
                    <option value="" key="0" />
                    {to2mPeople
                      ? to2mPeople.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/to-2-m-car" replace color="info">
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
  to2mPeople: storeState.to2mPerson.entities,
  to2mCarEntity: storeState.to2mCar.entity,
  loading: storeState.to2mCar.loading,
  updating: storeState.to2mCar.updating,
  updateSuccess: storeState.to2mCar.updateSuccess
});

const mapDispatchToProps = {
  getTo2MPeople,
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
)(To2mCarUpdate);
