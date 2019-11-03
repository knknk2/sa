import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBo2mOwner } from 'app/shared/model/bo-2-m-owner.model';
import { getEntities as getBo2MOwners } from 'app/entities/bo-2-m-owner/bo-2-m-owner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bo-2-m-car.reducer';
import { IBo2mCar } from 'app/shared/model/bo-2-m-car.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBo2mCarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBo2mCarUpdateState {
  isNew: boolean;
  bo2mOwnerId: string;
}

export class Bo2mCarUpdate extends React.Component<IBo2mCarUpdateProps, IBo2mCarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      bo2mOwnerId: '0',
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

    this.props.getBo2MOwners();
  }

  saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    if (errors.length === 0) {
      const { bo2mCarEntity } = this.props;
      const entity = {
        ...bo2mCarEntity,
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
    this.props.history.push('/entity/bo-2-m-car');
  };

  render() {
    const { bo2mCarEntity, bo2mOwners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.bo2mCar.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.bo2mCar.home.createOrEditLabel">Create or edit a Bo2mCar</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bo2mCarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="bo-2-m-car-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="bo-2-m-car-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="bo-2-m-car-name">
                    <Translate contentKey="sampleappApp.bo2mCar.name">Name</Translate>
                  </Label>
                  <AvField id="bo-2-m-car-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="bo-2-m-car-createdAt">
                    <Translate contentKey="sampleappApp.bo2mCar.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="bo-2-m-car-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.bo2mCarEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="bo-2-m-car-bo2mOwner">
                    <Translate contentKey="sampleappApp.bo2mCar.bo2mOwner">Bo 2 M Owner</Translate>
                  </Label>
                  <AvInput
                    id="bo-2-m-car-bo2mOwner"
                    type="select"
                    className="form-control"
                    name="bo2mOwner.id"
                    value={isNew ? bo2mOwners[0] && bo2mOwners[0].id : bo2mCarEntity.bo2mOwner.id}
                    required
                  >
                    {bo2mOwners
                      ? bo2mOwners.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>
                    <Translate contentKey="entity.validation.required">This field is required.</Translate>
                  </AvFeedback>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/bo-2-m-car" replace color="info">
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
  bo2mOwners: storeState.bo2mOwner.entities,
  bo2mCarEntity: storeState.bo2mCar.entity,
  loading: storeState.bo2mCar.loading,
  updating: storeState.bo2mCar.updating,
  updateSuccess: storeState.bo2mCar.updateSuccess
});

const mapDispatchToProps = {
  getBo2MOwners,
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
)(Bo2mCarUpdate);
