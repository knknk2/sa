import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './fields.reducer';
import { IFields } from 'app/shared/model/fields.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFieldsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFieldsUpdateState {
  isNew: boolean;
}

export class FieldsUpdate extends React.Component<IFieldsUpdateProps, IFieldsUpdateState> {
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

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.date2 = convertDateTimeToServer(values.date2);
    values.date3 = convertDateTimeToServer(values.date3);

    if (errors.length === 0) {
      const { fieldsEntity } = this.props;
      const entity = {
        ...fieldsEntity,
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
    this.props.history.push('/entity/fields');
  };

  render() {
    const { fieldsEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { blob, blobContentType, blob2, blob2ContentType, blob3 } = fieldsEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.fields.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.fields.home.createOrEditLabel">Create or edit a Fields</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fieldsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="fields-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="fields-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="strLabel" for="fields-str">
                    <Translate contentKey="sampleappApp.fields.str">Str</Translate>
                  </Label>
                  <AvField
                    id="fields-str"
                    type="text"
                    name="str"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 2, errorMessage: translate('entity.validation.minlength', { min: 2 }) },
                      maxLength: { value: 255, errorMessage: translate('entity.validation.maxlength', { max: 255 }) },
                      pattern: {
                        value: '^[a-zA-Z0-9]*$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]*$' })
                      }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="num1Label" for="fields-num1">
                    <Translate contentKey="sampleappApp.fields.num1">Num 1</Translate>
                  </Label>
                  <AvField
                    id="fields-num1"
                    type="string"
                    className="form-control"
                    name="num1"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      max: { value: 100, errorMessage: translate('entity.validation.max', { max: 100 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="num2Label" for="fields-num2">
                    <Translate contentKey="sampleappApp.fields.num2">Num 2</Translate>
                  </Label>
                  <AvField
                    id="fields-num2"
                    type="string"
                    className="form-control"
                    name="num2"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      max: { value: 1000, errorMessage: translate('entity.validation.max', { max: 1000 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="num3Label" for="fields-num3">
                    <Translate contentKey="sampleappApp.fields.num3">Num 3</Translate>
                  </Label>
                  <AvField
                    id="fields-num3"
                    type="string"
                    className="form-control"
                    name="num3"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      max: { value: 100, errorMessage: translate('entity.validation.max', { max: 100 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="num4Label" for="fields-num4">
                    <Translate contentKey="sampleappApp.fields.num4">Num 4</Translate>
                  </Label>
                  <AvField
                    id="fields-num4"
                    type="string"
                    className="form-control"
                    name="num4"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      max: { value: 100, errorMessage: translate('entity.validation.max', { max: 100 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="num5Label" for="fields-num5">
                    <Translate contentKey="sampleappApp.fields.num5">Num 5</Translate>
                  </Label>
                  <AvField
                    id="fields-num5"
                    type="text"
                    name="num5"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      max: { value: 100, errorMessage: translate('entity.validation.max', { max: 100 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="date1Label" for="fields-date1">
                    <Translate contentKey="sampleappApp.fields.date1">Date 1</Translate>
                  </Label>
                  <AvField
                    id="fields-date1"
                    type="date"
                    className="form-control"
                    name="date1"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="date2Label" for="fields-date2">
                    <Translate contentKey="sampleappApp.fields.date2">Date 2</Translate>
                  </Label>
                  <AvInput
                    id="fields-date2"
                    type="datetime-local"
                    className="form-control"
                    name="date2"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.fieldsEntity.date2)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="date3Label" for="fields-date3">
                    <Translate contentKey="sampleappApp.fields.date3">Date 3</Translate>
                  </Label>
                  <AvInput
                    id="fields-date3"
                    type="datetime-local"
                    className="form-control"
                    name="date3"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.fieldsEntity.date3)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="date4Label" for="fields-date4">
                    <Translate contentKey="sampleappApp.fields.date4">Date 4</Translate>
                  </Label>
                  <AvField
                    id="fields-date4"
                    type="text"
                    name="date4"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="uuidLabel" for="fields-uuid">
                    <Translate contentKey="sampleappApp.fields.uuid">Uuid</Translate>
                  </Label>
                  <AvField
                    id="fields-uuid"
                    type="text"
                    name="uuid"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="boolLabel" check>
                    <AvInput id="fields-bool" type="checkbox" className="form-control" name="bool" />
                    <Translate contentKey="sampleappApp.fields.bool">Bool</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="enumerationLabel" for="fields-enumeration">
                    <Translate contentKey="sampleappApp.fields.enumeration">Enumeration</Translate>
                  </Label>
                  <AvInput
                    id="fields-enumeration"
                    type="select"
                    className="form-control"
                    name="enumeration"
                    value={(!isNew && fieldsEntity.enumeration) || 'VALID'}
                  >
                    <option value="VALID">{translate('sampleappApp.Enum1.VALID')}</option>
                    <option value="INVALID">{translate('sampleappApp.Enum1.INVALID')}</option>
                    <option value="CANCELED">{translate('sampleappApp.Enum1.CANCELED')}</option>
                    <option value="INACTIVE">{translate('sampleappApp.Enum1.INACTIVE')}</option>
                    <option value="ACTIVE">{translate('sampleappApp.Enum1.ACTIVE')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="blobLabel" for="blob">
                      <Translate contentKey="sampleappApp.fields.blob">Blob</Translate>
                    </Label>
                    <br />
                    {blob ? (
                      <div>
                        <a onClick={openFile(blobContentType, blob)}>
                          <img src={`data:${blobContentType};base64,${blob}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {blobContentType}, {byteSize(blob)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('blob')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_blob" type="file" onChange={this.onBlobChange(true, 'blob')} accept="image/*" />
                    <AvInput
                      type="hidden"
                      name="blob"
                      value={blob}
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="blob2Label" for="blob2">
                      <Translate contentKey="sampleappApp.fields.blob2">Blob 2</Translate>
                    </Label>
                    <br />
                    {blob2 ? (
                      <div>
                        <a onClick={openFile(blob2ContentType, blob2)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {blob2ContentType}, {byteSize(blob2)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('blob2')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_blob2" type="file" onChange={this.onBlobChange(false, 'blob2')} />
                    <AvInput
                      type="hidden"
                      name="blob2"
                      value={blob2}
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="blob3Label" for="fields-blob3">
                    <Translate contentKey="sampleappApp.fields.blob3">Blob 3</Translate>
                  </Label>
                  <AvInput
                    id="fields-blob3"
                    type="textarea"
                    name="blob3"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 2, errorMessage: translate('entity.validation.minlength', { min: 2 }) },
                      maxLength: { value: 255, errorMessage: translate('entity.validation.maxlength', { max: 255 }) },
                      pattern: {
                        value: '^[a-zA-Z0-9]*$',
                        errorMessage: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]*$' })
                      }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/fields" replace color="info">
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
  fieldsEntity: storeState.fields.entity,
  loading: storeState.fields.loading,
  updating: storeState.fields.updating,
  updateSuccess: storeState.fields.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FieldsUpdate);
