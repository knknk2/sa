import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-2-m-driver-dtomf.reducer';
import { IM2mDriverDTOMF } from 'app/shared/model/m-2-m-driver-dtomf.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IM2mDriverDTOMFDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class M2mDriverDTOMFDetail extends React.Component<IM2mDriverDTOMFDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { m2mDriverDTOMFEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.m2mDriverDTOMF.detail.title">M2mDriverDTOMF</Translate> [<b>{m2mDriverDTOMFEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.m2mDriverDTOMF.name">Name</Translate>
              </span>
            </dt>
            <dd>{m2mDriverDTOMFEntity.name}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-2-m-driver-dtomf" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-2-m-driver-dtomf/${m2mDriverDTOMFEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ m2mDriverDTOMF }: IRootState) => ({
  m2mDriverDTOMFEntity: m2mDriverDTOMF.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(M2mDriverDTOMFDetail);
