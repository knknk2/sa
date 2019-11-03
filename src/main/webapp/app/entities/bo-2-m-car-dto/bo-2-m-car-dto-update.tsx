import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBo2mOwnerDTO } from 'app/shared/model/bo-2-m-owner-dto.model';
import { getEntities as getBo2MOwnerDtos } from 'app/entities/bo-2-m-owner-dto/bo-2-m-owner-dto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bo-2-m-car-dto.reducer';
import { IBo2mCarDTO } from 'app/shared/model/bo-2-m-car-dto.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBo2mCarDTOUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBo2mCarDTOUpdateState {
  isNew: boolean;
  bo2mOwnerDTOId: string;
}

export class Bo2mCarDTOUpdate extends React.Component<IBo2mCarDTOUpdateProps, IBo2mCarDTOUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      bo2mOwnerDTOId: '0',
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

    this.props.getBo2MOwnerDtos();
  }

  saveEntity = (event, errors, values) => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    if (errors.length === 0) {
      const { bo2mCarDTOEntity } = this.props;
      const entity = {
        ...bo2mCarDTOEntity,
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
    this.props.history.push('/entity/bo-2-m-car-dto');
  };

  render() {
    const { bo2mCarDTOEntity, bo2mOwnerDTOS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.bo2mCarDTO.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.bo2mCarDTO.home.createOrEditLabel">Create or edit a Bo2mCarDTO</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bo2mCarDTOEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="bo-2-m-car-dto-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="bo-2-m-car-dto-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="bo-2-m-car-dto-name">
                    <Translate contentKey="sampleappApp.bo2mCarDTO.name">Name</Translate>
                  </Label>
                  <AvField id="bo-2-m-car-dto-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="bo-2-m-car-dto-createdAt">
                    <Translate contentKey="sampleappApp.bo2mCarDTO.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="bo-2-m-car-dto-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.bo2mCarDTOEntity.createdAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="bo-2-m-car-dto-bo2mOwnerDTO">
                    <Translate contentKey="sampleappApp.bo2mCarDTO.bo2mOwnerDTO">Bo 2 M Owner DTO</Translate>
                  </Label>
                  <AvInput id="bo-2-m-car-dto-bo2mOwnerDTO" type="select" className="form-control" name="bo2mOwnerDTOId" required>
                    {bo2mOwnerDTOS
                      ? bo2mOwnerDTOS.map(otherEntity => (
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
                <Button tag={Link} id="cancel-save" to="/entity/bo-2-m-car-dto" replace color="info">
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
  bo2mOwnerDTOS: storeState.bo2mOwnerDTO.entities,
  bo2mCarDTOEntity: storeState.bo2mCarDTO.entity,
  loading: storeState.bo2mCarDTO.loading,
  updating: storeState.bo2mCarDTO.updating,
  updateSuccess: storeState.bo2mCarDTO.updateSuccess
});

const mapDispatchToProps = {
  getBo2MOwnerDtos,
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
)(Bo2mCarDTOUpdate);
