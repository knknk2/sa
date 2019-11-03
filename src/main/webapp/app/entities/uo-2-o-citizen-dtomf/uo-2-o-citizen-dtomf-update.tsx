import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUo2oPassportDTOMF } from 'app/shared/model/uo-2-o-passport-dtomf.model';
import { getEntities as getUo2OPassportDtomfs } from 'app/entities/uo-2-o-passport-dtomf/uo-2-o-passport-dtomf.reducer';
import { getEntity, updateEntity, createEntity, reset } from './uo-2-o-citizen-dtomf.reducer';
import { IUo2oCitizenDTOMF } from 'app/shared/model/uo-2-o-citizen-dtomf.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUo2oCitizenDTOMFUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUo2oCitizenDTOMFUpdateState {
  isNew: boolean;
  uo2oPassportDTOMFId: string;
}

export class Uo2oCitizenDTOMFUpdate extends React.Component<IUo2oCitizenDTOMFUpdateProps, IUo2oCitizenDTOMFUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      uo2oPassportDTOMFId: '0',
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

    this.props.getUo2OPassportDtomfs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { uo2oCitizenDTOMFEntity } = this.props;
      const entity = {
        ...uo2oCitizenDTOMFEntity,
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
    this.props.history.push('/entity/uo-2-o-citizen-dtomf');
  };

  render() {
    const { uo2oCitizenDTOMFEntity, uo2oPassportDTOMFS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.uo2oCitizenDTOMF.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.uo2oCitizenDTOMF.home.createOrEditLabel">Create or edit a Uo2oCitizenDTOMF</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : uo2oCitizenDTOMFEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="uo-2-o-citizen-dtomf-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="uo-2-o-citizen-dtomf-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="uo-2-o-citizen-dtomf-name">
                    <Translate contentKey="sampleappApp.uo2oCitizenDTOMF.name">Name</Translate>
                  </Label>
                  <AvField id="uo-2-o-citizen-dtomf-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="uo-2-o-citizen-dtomf-uo2oPassportDTOMF">
                    <Translate contentKey="sampleappApp.uo2oCitizenDTOMF.uo2oPassportDTOMF">Uo 2 O Passport DTOMF</Translate>
                  </Label>
                  <AvInput id="uo-2-o-citizen-dtomf-uo2oPassportDTOMF" type="select" className="form-control" name="uo2oPassportDTOMFId">
                    <option value="" key="0" />
                    {uo2oPassportDTOMFS
                      ? uo2oPassportDTOMFS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/uo-2-o-citizen-dtomf" replace color="info">
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
  uo2oPassportDTOMFS: storeState.uo2oPassportDTOMF.entities,
  uo2oCitizenDTOMFEntity: storeState.uo2oCitizenDTOMF.entity,
  loading: storeState.uo2oCitizenDTOMF.loading,
  updating: storeState.uo2oCitizenDTOMF.updating,
  updateSuccess: storeState.uo2oCitizenDTOMF.updateSuccess
});

const mapDispatchToProps = {
  getUo2OPassportDtomfs,
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
)(Uo2oCitizenDTOMFUpdate);
