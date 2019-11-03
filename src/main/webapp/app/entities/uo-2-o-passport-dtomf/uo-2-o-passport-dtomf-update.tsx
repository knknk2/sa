import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './uo-2-o-passport-dtomf.reducer';
import { IUo2oPassportDTOMF } from 'app/shared/model/uo-2-o-passport-dtomf.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUo2oPassportDTOMFUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUo2oPassportDTOMFUpdateState {
  isNew: boolean;
}

export class Uo2oPassportDTOMFUpdate extends React.Component<IUo2oPassportDTOMFUpdateProps, IUo2oPassportDTOMFUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { uo2oPassportDTOMFEntity } = this.props;
      const entity = {
        ...uo2oPassportDTOMFEntity,
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
    this.props.history.push('/entity/uo-2-o-passport-dtomf');
  };

  render() {
    const { uo2oPassportDTOMFEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.uo2oPassportDTOMF.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.uo2oPassportDTOMF.home.createOrEditLabel">Create or edit a Uo2oPassportDTOMF</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : uo2oPassportDTOMFEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="uo-2-o-passport-dtomf-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="uo-2-o-passport-dtomf-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="uo-2-o-passport-dtomf-name">
                    <Translate contentKey="sampleappApp.uo2oPassportDTOMF.name">Name</Translate>
                  </Label>
                  <AvField id="uo-2-o-passport-dtomf-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/uo-2-o-passport-dtomf" replace color="info">
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
  uo2oPassportDTOMFEntity: storeState.uo2oPassportDTOMF.entity,
  loading: storeState.uo2oPassportDTOMF.loading,
  updating: storeState.uo2oPassportDTOMF.updating,
  updateSuccess: storeState.uo2oPassportDTOMF.updateSuccess
});

const mapDispatchToProps = {
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
)(Uo2oPassportDTOMFUpdate);
