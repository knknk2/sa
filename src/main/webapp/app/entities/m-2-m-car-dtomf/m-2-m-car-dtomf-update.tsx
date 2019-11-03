import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IM2mDriverDTOMF } from 'app/shared/model/m-2-m-driver-dtomf.model';
import { getEntities as getM2MDriverDtomfs } from 'app/entities/m-2-m-driver-dtomf/m-2-m-driver-dtomf.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-2-m-car-dtomf.reducer';
import { IM2mCarDTOMF } from 'app/shared/model/m-2-m-car-dtomf.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IM2mCarDTOMFUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IM2mCarDTOMFUpdateState {
  isNew: boolean;
  idsm2mDriverDTOMF: any[];
}

export class M2mCarDTOMFUpdate extends React.Component<IM2mCarDTOMFUpdateProps, IM2mCarDTOMFUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsm2mDriverDTOMF: [],
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

    this.props.getM2MDriverDtomfs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { m2mCarDTOMFEntity } = this.props;
      const entity = {
        ...m2mCarDTOMFEntity,
        ...values,
        m2mDriverDTOMFS: mapIdList(values.m2mDriverDTOMFS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/m-2-m-car-dtomf');
  };

  render() {
    const { m2mCarDTOMFEntity, m2mDriverDTOMFS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.m2mCarDTOMF.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.m2mCarDTOMF.home.createOrEditLabel">Create or edit a M2mCarDTOMF</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : m2mCarDTOMFEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="m-2-m-car-dtomf-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="m-2-m-car-dtomf-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="m-2-m-car-dtomf-name">
                    <Translate contentKey="sampleappApp.m2mCarDTOMF.name">Name</Translate>
                  </Label>
                  <AvField id="m-2-m-car-dtomf-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="m-2-m-car-dtomf-m2mDriverDTOMF">
                    <Translate contentKey="sampleappApp.m2mCarDTOMF.m2mDriverDTOMF">M 2 M Driver DTOMF</Translate>
                  </Label>
                  <AvInput
                    id="m-2-m-car-dtomf-m2mDriverDTOMF"
                    type="select"
                    multiple
                    className="form-control"
                    name="m2mDriverDTOMFS"
                    value={m2mCarDTOMFEntity.m2mDriverDTOMFS && m2mCarDTOMFEntity.m2mDriverDTOMFS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {m2mDriverDTOMFS
                      ? m2mDriverDTOMFS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-2-m-car-dtomf" replace color="info">
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
  m2mDriverDTOMFS: storeState.m2mDriverDTOMF.entities,
  m2mCarDTOMFEntity: storeState.m2mCarDTOMF.entity,
  loading: storeState.m2mCarDTOMF.loading,
  updating: storeState.m2mCarDTOMF.updating,
  updateSuccess: storeState.m2mCarDTOMF.updateSuccess
});

const mapDispatchToProps = {
  getM2MDriverDtomfs,
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
)(M2mCarDTOMFUpdate);
