import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUm2oOwner } from 'app/shared/model/um-2-o-owner.model';
import { getEntities as getUm2OOwners } from 'app/entities/um-2-o-owner/um-2-o-owner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './um-2-o-car.reducer';
import { IUm2oCar } from 'app/shared/model/um-2-o-car.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUm2oCarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUm2oCarUpdateState {
  isNew: boolean;
  um2oOwnerId: string;
}

export class Um2oCarUpdate extends React.Component<IUm2oCarUpdateProps, IUm2oCarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      um2oOwnerId: '0',
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

    this.props.getUm2OOwners();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { um2oCarEntity } = this.props;
      const entity = {
        ...um2oCarEntity,
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
    this.props.history.push('/entity/um-2-o-car');
  };

  render() {
    const { um2oCarEntity, um2oOwners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.um2oCar.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.um2oCar.home.createOrEditLabel">Create or edit a Um2oCar</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : um2oCarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="um-2-o-car-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="um-2-o-car-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="um-2-o-car-name">
                    <Translate contentKey="sampleappApp.um2oCar.name">Name</Translate>
                  </Label>
                  <AvField id="um-2-o-car-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="um-2-o-car-um2oOwner">
                    <Translate contentKey="sampleappApp.um2oCar.um2oOwner">Um 2 O Owner</Translate>
                  </Label>
                  <AvInput
                    id="um-2-o-car-um2oOwner"
                    type="select"
                    className="form-control"
                    name="um2oOwner.id"
                    value={isNew ? um2oOwners[0] && um2oOwners[0].id : um2oCarEntity.um2oOwner.id}
                    required
                  >
                    {um2oOwners
                      ? um2oOwners.map(otherEntity => (
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
                <Button tag={Link} id="cancel-save" to="/entity/um-2-o-car" replace color="info">
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
  um2oOwners: storeState.um2oOwner.entities,
  um2oCarEntity: storeState.um2oCar.entity,
  loading: storeState.um2oCar.loading,
  updating: storeState.um2oCar.updating,
  updateSuccess: storeState.um2oCar.updateSuccess
});

const mapDispatchToProps = {
  getUm2OOwners,
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
)(Um2oCarUpdate);
