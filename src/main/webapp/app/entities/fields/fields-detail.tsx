import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fields.reducer';
import { IFields } from 'app/shared/model/fields.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFieldsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FieldsDetail extends React.Component<IFieldsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fieldsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.fields.detail.title">Fields</Translate> [<b>{fieldsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="str">
                <Translate contentKey="sampleappApp.fields.str">Str</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.str}</dd>
            <dt>
              <span id="num1">
                <Translate contentKey="sampleappApp.fields.num1">Num 1</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.num1}</dd>
            <dt>
              <span id="num2">
                <Translate contentKey="sampleappApp.fields.num2">Num 2</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.num2}</dd>
            <dt>
              <span id="num3">
                <Translate contentKey="sampleappApp.fields.num3">Num 3</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.num3}</dd>
            <dt>
              <span id="num4">
                <Translate contentKey="sampleappApp.fields.num4">Num 4</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.num4}</dd>
            <dt>
              <span id="num5">
                <Translate contentKey="sampleappApp.fields.num5">Num 5</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.num5}</dd>
            <dt>
              <span id="date1">
                <Translate contentKey="sampleappApp.fields.date1">Date 1</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fieldsEntity.date1} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="date2">
                <Translate contentKey="sampleappApp.fields.date2">Date 2</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fieldsEntity.date2} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="date3">
                <Translate contentKey="sampleappApp.fields.date3">Date 3</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fieldsEntity.date3} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="date4">
                <Translate contentKey="sampleappApp.fields.date4">Date 4</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.date4}</dd>
            <dt>
              <span id="uuid">
                <Translate contentKey="sampleappApp.fields.uuid">Uuid</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.uuid}</dd>
            <dt>
              <span id="bool">
                <Translate contentKey="sampleappApp.fields.bool">Bool</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.bool ? 'true' : 'false'}</dd>
            <dt>
              <span id="enumeration">
                <Translate contentKey="sampleappApp.fields.enumeration">Enumeration</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.enumeration}</dd>
            <dt>
              <span id="blob">
                <Translate contentKey="sampleappApp.fields.blob">Blob</Translate>
              </span>
            </dt>
            <dd>
              {fieldsEntity.blob ? (
                <div>
                  <a onClick={openFile(fieldsEntity.blobContentType, fieldsEntity.blob)}>
                    <img src={`data:${fieldsEntity.blobContentType};base64,${fieldsEntity.blob}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {fieldsEntity.blobContentType}, {byteSize(fieldsEntity.blob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="blob2">
                <Translate contentKey="sampleappApp.fields.blob2">Blob 2</Translate>
              </span>
            </dt>
            <dd>
              {fieldsEntity.blob2 ? (
                <div>
                  <a onClick={openFile(fieldsEntity.blob2ContentType, fieldsEntity.blob2)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                  <span>
                    {fieldsEntity.blob2ContentType}, {byteSize(fieldsEntity.blob2)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="blob3">
                <Translate contentKey="sampleappApp.fields.blob3">Blob 3</Translate>
              </span>
            </dt>
            <dd>{fieldsEntity.blob3}</dd>
          </dl>
          <Button tag={Link} to="/entity/fields" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/fields/${fieldsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ fields }: IRootState) => ({
  fieldsEntity: fields.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FieldsDetail);
