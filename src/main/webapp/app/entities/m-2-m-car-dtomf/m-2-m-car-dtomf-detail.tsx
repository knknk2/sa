import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-2-m-car-dtomf.reducer';
import { IM2mCarDTOMF } from 'app/shared/model/m-2-m-car-dtomf.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IM2mCarDTOMFDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class M2mCarDTOMFDetail extends React.Component<IM2mCarDTOMFDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { m2mCarDTOMFEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.m2mCarDTOMF.detail.title">M2mCarDTOMF</Translate> [<b>{m2mCarDTOMFEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.m2mCarDTOMF.name">Name</Translate>
              </span>
            </dt>
            <dd>{m2mCarDTOMFEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.m2mCarDTOMF.m2mDriverDTOMF">M 2 M Driver DTOMF</Translate>
            </dt>
            <dd>
              {m2mCarDTOMFEntity.m2mDriverDTOMFS
                ? m2mCarDTOMFEntity.m2mDriverDTOMFS.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === m2mCarDTOMFEntity.m2mDriverDTOMFS.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/m-2-m-car-dtomf" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-2-m-car-dtomf/${m2mCarDTOMFEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ m2mCarDTOMF }: IRootState) => ({
  m2mCarDTOMFEntity: m2mCarDTOMF.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(M2mCarDTOMFDetail);
